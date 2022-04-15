import { IProduct } from '@/shared/model/product.model';
import { IGrocery } from '@/shared/model/grocery.model';
import { IUserCoop } from '@/shared/model/user-coop.model';

export interface IBasket {
  id?: string;
  products?: IProduct[] | null;
  grocery?: IGrocery | null;
  userCoop?: IUserCoop | null;
}

export class Basket implements IBasket {
  constructor(
    public id?: string,
    public products?: IProduct[] | null,
    public grocery?: IGrocery | null,
    public userCoop?: IUserCoop | null
  ) {}
}
