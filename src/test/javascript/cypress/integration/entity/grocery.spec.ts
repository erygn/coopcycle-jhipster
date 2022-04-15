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

describe('Grocery e2e test', () => {
  const groceryPageUrl = '/grocery';
  const groceryPageUrlPattern = new RegExp('/grocery(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const grocerySample = { id: '5527fc7b-dcee-455d-a8dc-da68bf3ae1c0', adress: 'withdrawal viral Configurable' };

  let grocery: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/groceries+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/groceries').as('postEntityRequest');
    cy.intercept('DELETE', '/api/groceries/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (grocery) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/groceries/${grocery.id}`,
      }).then(() => {
        grocery = undefined;
      });
    }
  });

  it('Groceries menu should load Groceries page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('grocery');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Grocery').should('exist');
    cy.url().should('match', groceryPageUrlPattern);
  });

  describe('Grocery page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(groceryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Grocery page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/grocery/new$'));
        cy.getEntityCreateUpdateHeading('Grocery');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', groceryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/groceries',
          body: grocerySample,
        }).then(({ body }) => {
          grocery = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/groceries+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [grocery],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(groceryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Grocery page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('grocery');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', groceryPageUrlPattern);
      });

      it('edit button click should load edit Grocery page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Grocery');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', groceryPageUrlPattern);
      });

      it('last delete button click should delete instance of Grocery', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('grocery').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', groceryPageUrlPattern);

        grocery = undefined;
      });
    });
  });

  describe('new Grocery page', () => {
    beforeEach(() => {
      cy.visit(`${groceryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Grocery');
    });

    it('should create an instance of Grocery', () => {
      cy.get(`[data-cy="id"]`).type('820c1ad4-6b66-4ebe-92e5-9494775cb5f8').should('have.value', '820c1ad4-6b66-4ebe-92e5-9494775cb5f8');

      cy.get(`[data-cy="adress"]`).type('Keyboard').should('have.value', 'Keyboard');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        grocery = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', groceryPageUrlPattern);
    });
  });
});
