import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import GroceryService from '@/entities/grocery/grocery.service';
import { IGrocery } from '@/shared/model/grocery.model';

import RestaurantService from '@/entities/restaurant/restaurant.service';
import { IRestaurant } from '@/shared/model/restaurant.model';

import BasketService from '@/entities/basket/basket.service';
import { IBasket } from '@/shared/model/basket.model';

import CooperativeService from '@/entities/cooperative/cooperative.service';
import { ICooperative } from '@/shared/model/cooperative.model';

import { IUserCoop, UserCoop } from '@/shared/model/user-coop.model';
import UserCoopService from './user-coop.service';

const validations: any = {
  userCoop: {
    name: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class UserCoopUpdate extends Vue {
  @Inject('userCoopService') private userCoopService: () => UserCoopService;
  @Inject('alertService') private alertService: () => AlertService;

  public userCoop: IUserCoop = new UserCoop();

  @Inject('groceryService') private groceryService: () => GroceryService;

  public groceries: IGrocery[] = [];

  @Inject('restaurantService') private restaurantService: () => RestaurantService;

  public restaurants: IRestaurant[] = [];

  @Inject('basketService') private basketService: () => BasketService;

  public baskets: IBasket[] = [];

  @Inject('cooperativeService') private cooperativeService: () => CooperativeService;

  public cooperatives: ICooperative[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userCoopId) {
        vm.retrieveUserCoop(to.params.userCoopId);
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
    this.userCoop.cooperatives = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.userCoop.id) {
      this.userCoopService()
        .update(this.userCoop)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('coopcycleApp.userCoop.updated', { param: param.id });
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
      this.userCoopService()
        .create(this.userCoop)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('coopcycleApp.userCoop.created', { param: param.id });
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

  public retrieveUserCoop(userCoopId): void {
    this.userCoopService()
      .find(userCoopId)
      .then(res => {
        this.userCoop = res;
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
    this.restaurantService()
      .retrieve()
      .then(res => {
        this.restaurants = res.data;
      });
    this.basketService()
      .retrieve()
      .then(res => {
        this.baskets = res.data;
      });
    this.cooperativeService()
      .retrieve()
      .then(res => {
        this.cooperatives = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      return selectedVals.find(value => option.id === value.id) ?? option;
    }
    return option;
  }
}
