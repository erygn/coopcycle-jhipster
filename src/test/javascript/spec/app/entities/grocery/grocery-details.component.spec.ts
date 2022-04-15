/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import GroceryDetailComponent from '@/entities/grocery/grocery-details.vue';
import GroceryClass from '@/entities/grocery/grocery-details.component';
import GroceryService from '@/entities/grocery/grocery.service';
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
  describe('Grocery Management Detail Component', () => {
    let wrapper: Wrapper<GroceryClass>;
    let comp: GroceryClass;
    let groceryServiceStub: SinonStubbedInstance<GroceryService>;

    beforeEach(() => {
      groceryServiceStub = sinon.createStubInstance<GroceryService>(GroceryService);

      wrapper = shallowMount<GroceryClass>(GroceryDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { groceryService: () => groceryServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundGrocery = { id: 'ABC' };
        groceryServiceStub.find.resolves(foundGrocery);

        // WHEN
        comp.retrieveGrocery('ABC');
        await comp.$nextTick();

        // THEN
        expect(comp.grocery).toBe(foundGrocery);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundGrocery = { id: 'ABC' };
        groceryServiceStub.find.resolves(foundGrocery);

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
