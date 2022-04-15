/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CooperativeUpdateComponent from '@/entities/cooperative/cooperative-update.vue';
import CooperativeClass from '@/entities/cooperative/cooperative-update.component';
import CooperativeService from '@/entities/cooperative/cooperative.service';

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
  describe('Cooperative Management Update Component', () => {
    let wrapper: Wrapper<CooperativeClass>;
    let comp: CooperativeClass;
    let cooperativeServiceStub: SinonStubbedInstance<CooperativeService>;

    beforeEach(() => {
      cooperativeServiceStub = sinon.createStubInstance<CooperativeService>(CooperativeService);

      wrapper = shallowMount<CooperativeClass>(CooperativeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          cooperativeService: () => cooperativeServiceStub,
          alertService: () => new AlertService(),

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
        const entity = { id: 123 };
        comp.cooperative = entity;
        cooperativeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cooperativeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.cooperative = entity;
        cooperativeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cooperativeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCooperative = { id: 123 };
        cooperativeServiceStub.find.resolves(foundCooperative);
        cooperativeServiceStub.retrieve.resolves([foundCooperative]);

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
