import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IUserCoop } from '@/shared/model/user-coop.model';

import UserCoopService from './user-coop.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class UserCoop extends Vue {
  @Inject('userCoopService') private userCoopService: () => UserCoopService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public userCoops: IUserCoop[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllUserCoops();
  }

  public clear(): void {
    this.retrieveAllUserCoops();
  }

  public retrieveAllUserCoops(): void {
    this.isFetching = true;
    this.userCoopService()
      .retrieve()
      .then(
        res => {
          this.userCoops = res.data;
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

  public prepareRemove(instance: IUserCoop): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeUserCoop(): void {
    this.userCoopService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('coopcycleApp.userCoop.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllUserCoops();
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
