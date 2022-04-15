import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPayment } from '@/shared/model/payment.model';

import PaymentService from './payment.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Payment extends Vue {
  @Inject('paymentService') private paymentService: () => PaymentService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public payments: IPayment[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPayments();
  }

  public clear(): void {
    this.retrieveAllPayments();
  }

  public retrieveAllPayments(): void {
    this.isFetching = true;
    this.paymentService()
      .retrieve()
      .then(
        res => {
          this.payments = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IPayment): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePayment(): void {
    this.paymentService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('coopcycleApp.payment.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPayments();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
