<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="grocery">
        <h2 class="jh-entity-heading" data-cy="groceryDetailsHeading">
          <span v-text="$t('coopcycleApp.grocery.detail.title')">Grocery</span> {{ grocery.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('coopcycleApp.grocery.adress')">Adress</span>
          </dt>
          <dd>
            <span>{{ grocery.adress }}</span>
          </dd>
          <dt>
            <span v-text="$t('coopcycleApp.grocery.basket')">Basket</span>
          </dt>
          <dd>
            <div v-if="grocery.basket">
              <router-link :to="{ name: 'BasketView', params: { basketId: grocery.basket.id } }">{{ grocery.basket.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('coopcycleApp.grocery.userCoop')">User Coop</span>
          </dt>
          <dd>
            <div v-if="grocery.userCoop">
              <router-link :to="{ name: 'UserCoopView', params: { userCoopId: grocery.userCoop.id } }">{{
                grocery.userCoop.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="grocery.id" :to="{ name: 'GroceryEdit', params: { groceryId: grocery.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./grocery-details.component.ts"></script>
