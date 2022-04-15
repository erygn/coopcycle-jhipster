/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import GroceryUpdateComponent from '@/entities/grocery/grocery-update.vue';
import GroceryClass from '@/entities/grocery/grocery-update.component';
import GroceryService from '@/entities/grocery/grocery.service';

import BasketService from '@/entities/basket/basket.service';

import PaymentService from '@/entities/payment/payment.service';

import UserCoopService from '@/entities/user-coop/user-coop.service';
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
  describe('Grocery Management Update Component', () => {
    let wrapper: Wrapper<GroceryClass>;
    let comp: GroceryClass;
    let groceryServiceStub: SinonStubbedInstance<GroceryService>;

    beforeEach(() => {
      groceryServiceStub = sinon.createStubInstance<GroceryService>(GroceryService);

      wrapper = shallowMount<GroceryClass>(GroceryUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          groceryService: () => groceryServiceStub,
          alertService: () => new AlertService(),

          basketService: () =>
            sinon.createStubInstance<BasketService>(BasketService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          paymentService: () =>
            sinon.createStubInstance<PaymentService>(PaymentService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          userCoopService: () =>
            sinon.createStubInstance<UserCoopService>(UserCoopService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 'ABC' };
        comp.grocery = entity;
        groceryServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(groceryServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.grocery = entity;
        groceryServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(groceryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundGrocery = { id: 'ABC' };
        groceryServiceStub.find.resolves(foundGrocery);
        groceryServiceStub.retrieve.resolves([foundGrocery]);

        // WHEN
        comp.beforeRouteEnter({ params: { groceryId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.grocery).toBe(foundGrocery);
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
