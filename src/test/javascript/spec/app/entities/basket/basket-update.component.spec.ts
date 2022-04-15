/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import BasketUpdateComponent from '@/entities/basket/basket-update.vue';
import BasketClass from '@/entities/basket/basket-update.component';
import BasketService from '@/entities/basket/basket.service';

import ProductService from '@/entities/product/product.service';

import GroceryService from '@/entities/grocery/grocery.service';

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
  describe('Basket Management Update Component', () => {
    let wrapper: Wrapper<BasketClass>;
    let comp: BasketClass;
    let basketServiceStub: SinonStubbedInstance<BasketService>;

    beforeEach(() => {
      basketServiceStub = sinon.createStubInstance<BasketService>(BasketService);

      wrapper = shallowMount<BasketClass>(BasketUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          basketService: () => basketServiceStub,
          alertService: () => new AlertService(),

          productService: () =>
            sinon.createStubInstance<ProductService>(ProductService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          groceryService: () =>
            sinon.createStubInstance<GroceryService>(GroceryService, {
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
        comp.basket = entity;
        basketServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(basketServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.basket = entity;
        basketServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(basketServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBasket = { id: 'ABC' };
        basketServiceStub.find.resolves(foundBasket);
        basketServiceStub.retrieve.resolves([foundBasket]);

        // WHEN
        comp.beforeRouteEnter({ params: { basketId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.basket).toBe(foundBasket);
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
