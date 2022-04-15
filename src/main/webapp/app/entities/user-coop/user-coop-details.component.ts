import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUserCoop } from '@/shared/model/user-coop.model';
import UserCoopService from './user-coop.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class UserCoopDetails extends Vue {
  @Inject('userCoopService') private userCoopService: () => UserCoopService;
  @Inject('alertService') private alertService: () => AlertService;

  public userCoop: IUserCoop = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userCoopId) {
        vm.retrieveUserCoop(to.params.userCoopId);
      }
    });
  }

  public retrieveUserCoop(userCoopId) {
    this.userCoopService()
      .find(userCoopId)
      .then(res => {
        this.userCoop = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
