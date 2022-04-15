import { entityItemSelector } from '../../support/commands';
import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('UserCoop e2e test', () => {
  const userCoopPageUrl = '/user-coop';
  const userCoopPageUrlPattern = new RegExp('/user-coop(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const userCoopSample = { name: 'Shirt' };

  let userCoop: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/user-coops+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/user-coops').as('postEntityRequest');
    cy.intercept('DELETE', '/api/user-coops/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (userCoop) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/user-coops/${userCoop.id}`,
      }).then(() => {
        userCoop = undefined;
      });
    }
  });

  it('UserCoops menu should load UserCoops page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('user-coop');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('UserCoop').should('exist');
    cy.url().should('match', userCoopPageUrlPattern);
  });

  describe('UserCoop page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(userCoopPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create UserCoop page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/user-coop/new$'));
        cy.getEntityCreateUpdateHeading('UserCoop');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', userCoopPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/user-coops',
          body: userCoopSample,
        }).then(({ body }) => {
          userCoop = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/user-coops+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [userCoop],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(userCoopPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details UserCoop page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('userCoop');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', userCoopPageUrlPattern);
      });

      it('edit button click should load edit UserCoop page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('UserCoop');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', userCoopPageUrlPattern);
      });

      it('last delete button click should delete instance of UserCoop', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('userCoop').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', userCoopPageUrlPattern);

        userCoop = undefined;
      });
    });
  });

  describe('new UserCoop page', () => {
    beforeEach(() => {
      cy.visit(`${userCoopPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('UserCoop');
    });

    it('should create an instance of UserCoop', () => {
      cy.get(`[data-cy="name"]`).type('Reactive').should('have.value', 'Reactive');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        userCoop = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', userCoopPageUrlPattern);
    });
  });
});
