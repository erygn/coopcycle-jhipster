import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IGrocery } from '@/shared/model/grocery.model';

import GroceryService from './grocery.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Grocery extends Vue {
  @Inject('groceryService') private groceryService: () => GroceryService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public groceries: IGrocery[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllGrocerys();
  }

  public clear(): void {
    this.retrieveAllGrocerys();
  }

  public retrieveAllGrocerys(): void {
    this.isFetching = true;
    this.groceryService()
      .retrieve()
      .then(
        res => {
          this.groceries = res.data;
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

  public prepareRemove(instance: IGrocery): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeGrocery(): void {
    this.groceryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('coopcycleApp.grocery.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllGrocerys();
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
