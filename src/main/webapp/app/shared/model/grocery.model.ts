import { IBasket } from '@/shared/model/basket.model';
import { IPayment } from '@/shared/model/payment.model';
import { IUserCoop } from '@/shared/model/user-coop.model';

export interface IGrocery {
  id?: string;
  adress?: string;
  basket?: IBasket | null;
  payment?: IPayment | null;
  userCoop?: IUserCoop | null;
}

export class Grocery implements IGrocery {
  constructor(
    public id?: string,
    public adress?: string,
    public basket?: IBasket | null,
    public payment?: IPayment | null,
    public userCoop?: IUserCoop | null
  ) {}
}
