/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import UserCoopComponent from '@/entities/user-coop/user-coop.vue';
import UserCoopClass from '@/entities/user-coop/user-coop.component';
import UserCoopService from '@/entities/user-coop/user-coop.service';
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
  describe('UserCoop Management Component', () => {
    let wrapper: Wrapper<UserCoopClass>;
    let comp: UserCoopClass;
    let userCoopServiceStub: SinonStubbedInstance<UserCoopService>;

    beforeEach(() => {
      userCoopServiceStub = sinon.createStubInstance<UserCoopService>(UserCoopService);
      userCoopServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<UserCoopClass>(UserCoopComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          userCoopService: () => userCoopServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      userCoopServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllUserCoops();
      await comp.$nextTick();

      // THEN
      expect(userCoopServiceStub.retrieve.called).toBeTruthy();
      expect(comp.userCoops[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      userCoopServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(userCoopServiceStub.retrieve.callCount).toEqual(1);

      comp.removeUserCoop();
      await comp.$nextTick();

      // THEN
      expect(userCoopServiceStub.delete.called).toBeTruthy();
      expect(userCoopServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
