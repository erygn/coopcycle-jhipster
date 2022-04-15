/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PaymentUpdateComponent from '@/entities/payment/payment-update.vue';
import PaymentClass from '@/entities/payment/payment-update.component';
import PaymentService from '@/entities/payment/payment.service';

import GroceryService from '@/entities/grocery/grocery.service';
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
  describe('Payment Management Update Component', () => {
    let wrapper: Wrapper<PaymentClass>;
    let comp: PaymentClass;
    let paymentServiceStub: SinonStubbedInstance<PaymentService>;

    beforeEach(() => {
      paymentServiceStub = sinon.createStubInstance<PaymentService>(PaymentService);

      wrapper = shallowMount<PaymentClass>(PaymentUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          paymentService: () => paymentServiceStub,
          alertService: () => new AlertService(),

          groceryService: () =>
            sinon.createStubInstance<GroceryService>(GroceryService, {
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
        comp.payment = entity;
        paymentServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(paymentServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.payment = entity;
        paymentServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(paymentServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPayment = { id: 'ABC' };
        paymentServiceStub.find.resolves(foundPayment);
        paymentServiceStub.retrieve.resolves([foundPayment]);

        // WHEN
        comp.beforeRouteEnter({ params: { paymentId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.payment).toBe(foundPayment);
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
