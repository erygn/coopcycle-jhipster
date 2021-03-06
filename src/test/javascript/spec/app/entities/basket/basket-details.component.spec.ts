/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import BasketDetailComponent from '@/entities/basket/basket-details.vue';
import BasketClass from '@/entities/basket/basket-details.component';
import BasketService from '@/entities/basket/basket.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Basket Management Detail Component', () => {
    let wrapper: Wrapper<BasketClass>;
    let comp: BasketClass;
    let basketServiceStub: SinonStubbedInstance<BasketService>;

    beforeEach(() => {
      basketServiceStub = sinon.createStubInstance<BasketService>(BasketService);

      wrapper = shallowMount<BasketClass>(BasketDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { basketService: () => basketServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundBasket = { id: 'ABC' };
        basketServiceStub.find.resolves(foundBasket);

        // WHEN
        comp.retrieveBasket('ABC');
        await comp.$nextTick();

        // THEN
        expect(comp.basket).toBe(foundBasket);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBasket = { id: 'ABC' };
        basketServiceStub.find.resolves(foundBasket);

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
