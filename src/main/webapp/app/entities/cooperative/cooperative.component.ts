import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICooperative } from '@/shared/model/cooperative.model';

import CooperativeService from './cooperative.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Cooperative extends Vue {
  @Inject('cooperativeService') private cooperativeService: () => CooperativeService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public cooperatives: ICooperative[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCooperatives();
  }

  public clear(): void {
    this.retrieveAllCooperatives();
  }

  public retrieveAllCooperatives(): void {
    this.isFetching = true;
    this.cooperativeService()
      .retrieve()
      .then(
        res => {
          this.cooperatives = res.data;
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

  public prepareRemove(instance: ICooperative): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCooperative(): void {
    this.cooperativeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('coopcycleApp.cooperative.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllCooperatives();
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
