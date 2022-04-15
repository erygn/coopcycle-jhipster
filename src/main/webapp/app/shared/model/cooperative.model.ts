import { IUserCoop } from '@/shared/model/user-coop.model';

export interface ICooperative {
  id?: number;
  name?: string;
  userCoops?: IUserCoop[] | null;
}

export class Cooperative implements ICooperative {
  constructor(public id?: number, public name?: string, public userCoops?: IUserCoop[] | null) {}
}
