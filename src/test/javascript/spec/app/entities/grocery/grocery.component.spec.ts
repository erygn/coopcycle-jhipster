/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import GroceryComponent from '@/entities/grocery/grocery.vue';
import GroceryClass from '@/entities/grocery/grocery.component';
import GroceryService from '@/entities/grocery/grocery.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Grocery Management Component', () => {
    let wrapper: Wrapper<GroceryClass>;
    let comp: GroceryClass;
    let groceryServiceStub: SinonStubbedInstance<GroceryService>;

    beforeEach(() => {
      groceryServiceStub = sinon.createStubInstance<GroceryService>(GroceryService);
      groceryServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<GroceryClass>(GroceryComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          groceryService: () => groceryServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      groceryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

      // WHEN
      comp.retrieveAllGrocerys();
      await comp.$nextTick();

      // THEN
      expect(groceryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.groceries[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      groceryServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 'ABC' });
      expect(groceryServiceStub.retrieve.callCount).toEqual(1);

      comp.removeGrocery();
      await comp.$nextTick();

      // THEN
      expect(groceryServiceStub.delete.called).toBeTruthy();
      expect(groceryServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
