import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, minLength, decimal, minValue, numeric } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import BasketService from '@/entities/basket/basket.service';
import { IBasket } from '@/shared/model/basket.model';

import RestaurantService from '@/entities/restaurant/restaurant.service';
import { IRestaurant } from '@/shared/model/restaurant.model';

import { IProduct, Product } from '@/shared/model/product.model';
import ProductService from './product.service';

const validations: any = {
  product: {
    name: {
      required,
      minLength: minLength(4),
    },
    price: {
      required,
      decimal,
      min: minValue(0),
    },
    quantity: {
      required,
      numeric,
      min: minValue(0),
    },
  },
};

@Component({
  validations,
})
export default class ProductUpdate extends Vue {
  @Inject('productService') private productService: () => ProductService;
  @Inject('alertService') private alertService: () => AlertService;

  public product: IProduct = new Product();

  @Inject('basketService') private basketService: () => BasketService;

  public baskets: IBasket[] = [];

  @Inject('restaurantService') private restaurantService: () => RestaurantService;

  public restaurants: IRestaurant[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.productId) {
        vm.retrieveProduct(to.params.productId);
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
    if (this.product.id) {
      this.productService()
        .update(this.product)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('coopcycleApp.product.updated', { param: param.id });
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
      this.productService()
        .create(this.product)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('coopcycleApp.product.created', { param: param.id });
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

  public retrieveProduct(productId): void {
    this.productService()
      .find(productId)
      .then(res => {
        this.product = res;
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
    this.restaurantService()
      .retrieve()
      .then(res => {
        this.restaurants = res.data;
      });
  }
}
