/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CooperativeDetailComponent from '@/entities/cooperative/cooperative-details.vue';
import CooperativeClass from '@/entities/cooperative/cooperative-details.component';
import CooperativeService from '@/entities/cooperative/cooperative.service';
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
  describe('Cooperative Management Detail Component', () => {
    let wrapper: Wrapper<CooperativeClass>;
    let comp: CooperativeClass;
    let cooperativeServiceStub: SinonStubbedInstance<CooperativeService>;

    beforeEach(() => {
      cooperativeServiceStub = sinon.createStubInstance<CooperativeService>(CooperativeService);

      wrapper = shallowMount<CooperativeClass>(CooperativeDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { cooperativeService: () => cooperativeServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCooperative = { id: 123 };
        cooperativeServiceStub.find.resolves(foundCooperative);

        // WHEN
        comp.retrieveCooperative(123);
        await comp.$nextTick();

        // THEN
        expect(comp.cooperative).toBe(foundCooperative);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCooperative = { id: 123 };
        cooperativeServiceStub.find.resolves(foundCooperative);

        // WHEN
        comp.beforeRouteEnter({ params: { cooperativeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.cooperative).toBe(foundCooperative);
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
