import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICooperative } from '@/shared/model/cooperative.model';
import CooperativeService from './cooperative.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CooperativeDetails extends Vue {
  @Inject('cooperativeService') private cooperativeService: () => CooperativeService;
  @Inject('alertService') private alertService: () => AlertService;

  public cooperative: ICooperative = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cooperativeId) {
        vm.retrieveCooperative(to.params.cooperativeId);
      }
    });
  }

  public retrieveCooperative(cooperativeId) {
    this.cooperativeService()
      .find(cooperativeId)
      .then(res => {
        this.cooperative = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
