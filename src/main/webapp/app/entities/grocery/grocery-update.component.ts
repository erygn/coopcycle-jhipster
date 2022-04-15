import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import BasketService from '@/entities/basket/basket.service';
import { IBasket } from '@/shared/model/basket.model';

import PaymentService from '@/entities/payment/payment.service';
import { IPayment } from '@/shared/model/payment.model';

import UserCoopService from '@/entities/user-coop/user-coop.service';
import { IUserCoop } from '@/shared/model/user-coop.model';

import { IGrocery, Grocery } from '@/shared/model/grocery.model';
import GroceryService from './grocery.service';

const validations: any = {
  grocery: {
    adress: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class GroceryUpdate extends Vue {
  @Inject('groceryService') private groceryService: () => GroceryService;
  @Inject('alertService') private alertService: () => AlertService;

  public grocery: IGrocery = new Grocery();

  @Inject('basketService') private basketService: () => BasketService;

  public baskets: IBasket[] = [];

  @Inject('paymentService') private paymentService: () => PaymentService;

  public payments: IPayment[] = [];

  @Inject('userCoopService') private userCoopService: () => UserCoopService;

  public userCoops: IUserCoop[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.groceryId) {
        vm.retrieveGrocery(to.params.groceryId);
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
    if (this.grocery.id) {
      this.groceryService()
        .update(this.grocery)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('coopcycleApp.grocery.updated', { param: param.id });
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
      this.groceryService()
        .create(this.grocery)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('coopcycleApp.grocery.created', { param: param.id });
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

  public retrieveGrocery(groceryId): void {
    this.groceryService()
      .find(groceryId)
      .then(res => {
        this.grocery = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.basketService()
      .retrieve()
      .then(res => {
        this.baskets = res.data;
      });
    this.paymentService()
      .retrieve()
      .then(res => {
        this.payments = res.data;
      });
    this.userCoopService()
      .retrieve()
      .then(res => {
        this.userCoops = res.data;
      });
  }
}
