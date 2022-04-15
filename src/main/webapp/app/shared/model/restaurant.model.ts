import { IProduct } from '@/shared/model/product.model';
import { IUserCoop } from '@/shared/model/user-coop.model';

export interface IRestaurant {
  id?: number;
  name?: string;
  location?: string;
  products?: IProduct[] | null;
  userCoop?: IUserCoop | null;
}

export class Restaurant implements IRestaurant {
  constructor(
    public id?: number,
    public name?: string,
    public location?: string,
    public products?: IProduct[] | null,
    public userCoop?: IUserCoop | null
  ) {}
}
