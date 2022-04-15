/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import UserCoopUpdateComponent from '@/entities/user-coop/user-coop-update.vue';
import UserCoopClass from '@/entities/user-coop/user-coop-update.component';
import UserCoopService from '@/entities/user-coop/user-coop.service';

import GroceryService from '@/entities/grocery/grocery.service';

import RestaurantService from '@/entities/restaurant/restaurant.service';

import BasketService from '@/entities/basket/basket.service';

import CooperativeService from '@/entities/cooperative/cooperative.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('UserCoop Management Update Component', () => {
    let wrapper: Wrapper<UserCoopClass>;
    let comp: UserCoopClass;
    let userCoopServiceStub: SinonStubbedInstance<UserCoopService>;

    beforeEach(() => {
      userCoopServiceStub = sinon.createStubInstance<UserCoopService>(UserCoopService);

      wrapper = shallowMount<UserCoopClass>(UserCoopUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          userCoopService: () => userCoopServiceStub,
          alertService: () => new AlertService(),

          groceryService: () =>
            sinon.createStubInstance<GroceryService>(GroceryService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          restaurantService: () =>
            sinon.createStubInstance<RestaurantService>(RestaurantService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          basketService: () =>
            sinon.createStubInstance<BasketService>(BasketService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          cooperativeService: () =>
            sinon.createStubInstance<CooperativeService>(CooperativeService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.userCoop = entity;
        userCoopServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(userCoopServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.userCoop = entity;
        userCoopServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(userCoopServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUserCoop = { id: 123 };
        userCoopServiceStub.find.resolves(foundUserCoop);
        userCoopServiceStub.retrieve.resolves([foundUserCoop]);

        // WHEN
        comp.beforeRouteEnter({ params: { userCoopId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.userCoop).toBe(foundUserCoop);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
