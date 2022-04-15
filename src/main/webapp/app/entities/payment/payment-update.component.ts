import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import GroceryService from '@/entities/grocery/grocery.service';
import { IGrocery } from '@/shared/model/grocery.model';

import { IPayment, Payment } from '@/shared/model/payment.model';
import PaymentService from './payment.service';

const validations: any = {
  payment: {
    amount: {
      required,
      numeric,
      min: minValue(0),
    },
  },
};

@Component({
  validations,
})
export default class PaymentUpdate extends Vue {
  @Inject('paymentService') private paymentService: () => PaymentService;
  @Inject('alertService') private alertService: () => AlertService;

  public payment: IPayment = new Payment();

  @Inject('groceryService') private groceryService: () => GroceryService;

  public groceries: IGrocery[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.paymentId) {
        vm.retrievePayment(to.params.paymentId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.payment.id) {
      this.paymentService()
        .update(this.payment)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('coopcycleApp.payment.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.paymentService()
        .create(this.payment)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('coopcycleApp.payment.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrievePayment(paymentId): void {
    this.paymentService()
      .find(paymentId)
      .then(res => {
        this.payment = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.groceryService()
      .retrieve()
      .then(res => {
        this.groceries = res.data;
      });
  }
}
