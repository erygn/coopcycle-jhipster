import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const UserCoop = () => import('@/entities/user-coop/user-coop.vue');
// prettier-ignore
const UserCoopUpdate = () => import('@/entities/user-coop/user-coop-update.vue');
// prettier-ignore
const UserCoopDetails = () => import('@/entities/user-coop/user-coop-details.vue');
// prettier-ignore
const Grocery = () => import('@/entities/grocery/grocery.vue');
// prettier-ignore
const GroceryUpdate = () => import('@/entities/grocery/grocery-update.vue');
// prettier-ignore
const GroceryDetails = () => import('@/entities/grocery/grocery-details.vue');
// prettier-ignore
const Payment = () => import('@/entities/payment/payment.vue');
// prettier-ignore
const PaymentUpdate = () => import('@/entities/payment/payment-update.vue');
// prettier-ignore
const PaymentDetails = () => import('@/entities/payment/payment-details.vue');
// prettier-ignore
const Basket = () => import('@/entities/basket/basket.vue');
// prettier-ignore
const BasketUpdate = () => import('@/entities/basket/basket-update.vue');
// prettier-ignore
const BasketDetails = () => import('@/entities/basket/basket-details.vue');
// prettier-ignore
const Product = () => import('@/entities/product/product.vue');
// prettier-ignore
const ProductUpdate = () => import('@/entities/product/product-update.vue');
// prettier-ignore
const ProductDetails = () => import('@/entities/product/product-details.vue');
// prettier-ignore
const Restaurant = () => import('@/entities/restaurant/restaurant.vue');
// prettier-ignore
const RestaurantUpdate = () => import('@/entities/restaurant/restaurant-update.vue');
// prettier-ignore
const RestaurantDetails = () => import('@/entities/restaurant/restaurant-details.vue');
// prettier-ignore
const Cooperative = () => import('@/entities/cooperative/cooperative.vue');
// prettier-ignore
const CooperativeUpdate = () => import('@/entities/cooperative/cooperative-update.vue');
// prettier-ignore
const CooperativeDetails = () => import('@/entities/cooperative/cooperative-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'user-coop',
      name: 'UserCoop',
      component: UserCoop,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'user-coop/new',
      name: 'UserCoopCreate',
      component: UserCoopUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'user-coop/:userCoopId/edit',
      name: 'UserCoopEdit',
      component: UserCoopUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'user-coop/:userCoopId/view',
      name: 'UserCoopView',
      component: UserCoopDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'grocery',
      name: 'Grocery',
      component: Grocery,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'grocery/new',
      name: 'GroceryCreate',
      component: GroceryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'grocery/:groceryId/edit',
      name: 'GroceryEdit',
      component: GroceryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'grocery/:groceryId/view',
      name: 'GroceryView',
      component: GroceryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment',
      name: 'Payment',
      component: Payment,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment/new',
      name: 'PaymentCreate',
      component: PaymentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment/:paymentId/edit',
      name: 'PaymentEdit',
      component: PaymentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment/:paymentId/view',
      name: 'PaymentView',
      component: PaymentDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'basket',
      name: 'Basket',
      component: Basket,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'basket/new',
      name: 'BasketCreate',
      component: BasketUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'basket/:basketId/edit',
      name: 'BasketEdit',
      component: BasketUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'basket/:basketId/view',
      name: 'BasketView',
      component: BasketDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product',
      name: 'Product',
      component: Product,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/new',
      name: 'ProductCreate',
      component: ProductUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/:productId/edit',
      name: 'ProductEdit',
      component: ProductUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/:productId/view',
      name: 'ProductView',
      component: ProductDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'restaurant',
      name: 'Restaurant',
      component: Restaurant,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'restaurant/new',
      name: 'RestaurantCreate',
      component: RestaurantUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'restaurant/:restaurantId/edit',
      name: 'RestaurantEdit',
      component: RestaurantUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'restaurant/:restaurantId/view',
      name: 'RestaurantView',
      component: RestaurantDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cooperative',
      name: 'Cooperative',
      component: Cooperative,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cooperative/new',
      name: 'CooperativeCreate',
      component: CooperativeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cooperative/:cooperativeId/edit',
      name: 'CooperativeEdit',
      component: CooperativeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cooperative/:cooperativeId/view',
      name: 'CooperativeView',
      component: CooperativeDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
