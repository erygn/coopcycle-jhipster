import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import ProductService from '@/entities/product/product.service';
import { IProduct } from '@/shared/model/product.model';

import GroceryService from '@/entities/grocery/grocery.service';
import { IGrocery } from '@/shared/model/grocery.model';

import UserCoopService from '@/entities/user-coop/user-coop.service';
import { IUserCoop } from '@/shared/model/user-coop.model';

import { IBasket, Basket } from '@/shared/model/basket.model';
import BasketService from './basket.service';

const validations: any = {
  basket: {},
};

@Component({
  validations,
})
export default class BasketUpdate extends Vue {
  @Inject('basketService') private basketService: () => BasketService;
  @Inject('alertService') private alertService: () => AlertService;

  public basket: IBasket = new Basket();

  @Inject('productService') private productService: () => ProductService;

  public products: IProduct[] = [];

  @Inject('groceryService') private groceryService: () => GroceryService;

  public groceries: IGrocery[] = [];

  @Inject('userCoopService') private userCoopService: () => UserCoopService;

  public userCoops: IUserCoop[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.basketId) {
        vm.retrieveBasket(to.params.basketId);
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
    if (this.basket.id) {
      this.basketService()
        .update(this.basket)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('coopcycleApp.basket.updated', { param: param.id });
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
      this.basketService()
        .create(this.basket)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('coopcycleApp.basket.created', { param: param.id });
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

  public retrieveBasket(basketId): void {
    this.basketService()
      .find(basketId)
      .then(res => {
        this.basket = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.productService()
      .retrieve()
      .then(res => {
        this.products = res.data;
      });
    this.groceryService()
      .retrieve()
      .then(res => {
        this.groceries = res.data;
      });
    this.userCoopService()
      .retrieve()
      .then(res => {
        this.userCoops = res.data;
      });
  }
}
