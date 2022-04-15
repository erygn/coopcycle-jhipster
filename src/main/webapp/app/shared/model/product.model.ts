import { IBasket } from '@/shared/model/basket.model';
import { IRestaurant } from '@/shared/model/restaurant.model';

export interface IProduct {
  id?: number;
  name?: string;
  price?: number;
  quantity?: number;
  basket?: IBasket | null;
  restaurant?: IRestaurant | null;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public name?: string,
    public price?: number,
    public quantity?: number,
    public basket?: IBasket | null,
    public restaurant?: IRestaurant | null
  ) {}
}
