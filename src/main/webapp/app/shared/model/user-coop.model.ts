import { IGrocery } from '@/shared/model/grocery.model';
import { IRestaurant } from '@/shared/model/restaurant.model';
import { IBasket } from '@/shared/model/basket.model';
import { ICooperative } from '@/shared/model/cooperative.model';

export interface IUserCoop {
  id?: number;
  name?: string;
  groceries?: IGrocery[] | null;
  restaurants?: IRestaurant[] | null;
  baskets?: IBasket[] | null;
  cooperatives?: ICooperative[] | null;
}

export class UserCoop implements IUserCoop {
  constructor(
    public id?: number,
    public name?: string,
    public groceries?: IGrocery[] | null,
    public restaurants?: IRestaurant[] | null,
    public baskets?: IBasket[] | null,
    public cooperatives?: ICooperative[] | null
  ) {}
}
