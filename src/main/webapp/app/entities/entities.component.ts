import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import UserCoopService from './user-coop/user-coop.service';
import GroceryService from './grocery/grocery.service';
import PaymentService from './payment/payment.service';
import BasketService from './basket/basket.service';
import ProductService from './product/product.service';
import RestaurantService from './restaurant/restaurant.service';
import CooperativeService from './cooperative/cooperative.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('userCoopService') private userCoopService = () => new UserCoopService();
  @Provide('groceryService') private groceryService = () => new GroceryService();
  @Provide('paymentService') private paymentService = () => new PaymentService();
  @Provide('basketService') private basketService = () => new BasketService();
  @Provide('productService') private productService = () => new ProductService();
  @Provide('restaurantService') private restaurantService = () => new RestaurantService();
  @Provide('cooperativeService') private cooperativeService = () => new CooperativeService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
