<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="product">
        <h2 class="jh-entity-heading" data-cy="productDetailsHeading">
          <span v-text="$t('coopcycleApp.product.detail.title')">Product</span> {{ product.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('coopcycleApp.product.name')">Name</span>
          </dt>
          <dd>
            <span>{{ product.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('coopcycleApp.product.price')">Price</span>
          </dt>
          <dd>
            <span>{{ product.price }}</span>
          </dd>
          <dt>
            <span v-text="$t('coopcycleApp.product.quantity')">Quantity</span>
          </dt>
          <dd>
            <span>{{ product.quantity }}</span>
          </dd>
          <dt>
            <span v-text="$t('coopcycleApp.product.basket')">Basket</span>
          </dt>
          <dd>
            <div v-if="product.basket">
              <router-link :to="{ name: 'BasketView', params: { basketId: product.basket.id } }">{{ product.basket.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('coopcycleApp.product.restaurant')">Restaurant</span>
          </dt>
          <dd>
            <div v-if="product.restaurant">
              <router-link :to="{ name: 'RestaurantView', params: { restaurantId: product.restaurant.id } }">{{
                product.restaurant.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="product.id" :to="{ name: 'ProductEdit', params: { productId: product.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./product-details.component.ts"></script>
