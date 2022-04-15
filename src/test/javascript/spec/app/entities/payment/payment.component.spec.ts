/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PaymentComponent from '@/entities/payment/payment.vue';
import PaymentClass from '@/entities/payment/payment.component';
import PaymentService from '@/entities/payment/payment.service';
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
  describe('Payment Management Component', () => {
    let wrapper: Wrapper<PaymentClass>;
    let comp: PaymentClass;
    let paymentServiceStub: SinonStubbedInstance<PaymentService>;

    beforeEach(() => {
      paymentServiceStub = sinon.createStubInstance<PaymentService>(PaymentService);
      paymentServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PaymentClass>(PaymentComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          paymentService: () => paymentServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      paymentServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

      // WHEN
      comp.retrieveAllPayments();
      await comp.$nextTick();

      // THEN
      expect(paymentServiceStub.retrieve.called).toBeTruthy();
      expect(comp.payments[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      paymentServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 'ABC' });
      expect(paymentServiceStub.retrieve.callCount).toEqual(1);

      comp.removePayment();
      await comp.$nextTick();

      // THEN
      expect(paymentServiceStub.delete.called).toBeTruthy();
      expect(paymentServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
