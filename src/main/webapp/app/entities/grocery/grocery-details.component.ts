import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGrocery } from '@/shared/model/grocery.model';
import GroceryService from './grocery.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class GroceryDetails extends Vue {
  @Inject('groceryService') private groceryService: () => GroceryService;
  @Inject('alertService') private alertService: () => AlertService;

  public grocery: IGrocery = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.groceryId) {
        vm.retrieveGrocery(to.params.groceryId);
      }
    });
  }

  public retrieveGrocery(groceryId) {
    this.groceryService()
      .find(groceryId)
      .then(res => {
        this.grocery = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
