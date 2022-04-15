/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import UserCoopDetailComponent from '@/entities/user-coop/user-coop-details.vue';
import UserCoopClass from '@/entities/user-coop/user-coop-details.component';
import UserCoopService from '@/entities/user-coop/user-coop.service';
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
  describe('UserCoop Management Detail Component', () => {
    let wrapper: Wrapper<UserCoopClass>;
    let comp: UserCoopClass;
    let userCoopServiceStub: SinonStubbedInstance<UserCoopService>;

    beforeEach(() => {
      userCoopServiceStub = sinon.createStubInstance<UserCoopService>(UserCoopService);

      wrapper = shallowMount<UserCoopClass>(UserCoopDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { userCoopService: () => userCoopServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundUserCoop = { id: 123 };
        userCoopServiceStub.find.resolves(foundUserCoop);

        // WHEN
        comp.retrieveUserCoop(123);
        await comp.$nextTick();

        // THEN
        expect(comp.userCoop).toBe(foundUserCoop);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUserCoop = { id: 123 };
        userCoopServiceStub.find.resolves(foundUserCoop);

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
