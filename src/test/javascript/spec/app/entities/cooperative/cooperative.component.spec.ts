/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CooperativeComponent from '@/entities/cooperative/cooperative.vue';
import CooperativeClass from '@/entities/cooperative/cooperative.component';
import CooperativeService from '@/entities/cooperative/cooperative.service';
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
  describe('Cooperative Management Component', () => {
    let wrapper: Wrapper<CooperativeClass>;
    let comp: CooperativeClass;
    let cooperativeServiceStub: SinonStubbedInstance<CooperativeService>;

    beforeEach(() => {
      cooperativeServiceStub = sinon.createStubInstance<CooperativeService>(CooperativeService);
      cooperativeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CooperativeClass>(CooperativeComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          cooperativeService: () => cooperativeServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      cooperativeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCooperatives();
      await comp.$nextTick();

      // THEN
      expect(cooperativeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.cooperatives[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      cooperativeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(cooperativeServiceStub.retrieve.callCount).toEqual(1);

      comp.removeCooperative();
      await comp.$nextTick();

      // THEN
      expect(cooperativeServiceStub.delete.called).toBeTruthy();
      expect(cooperativeServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
