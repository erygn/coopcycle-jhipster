<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="basket">
        <h2 class="jh-entity-heading" data-cy="basketDetailsHeading">
          <span v-text="$t('coopcycleApp.basket.detail.title')">Basket</span> {{ basket.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('coopcycleApp.basket.userCoop')">User Coop</span>
          </dt>
          <dd>
            <div v-if="basket.userCoop">
              <router-link :to="{ name: 'UserCoopView', params: { userCoopId: basket.userCoop.id } }">{{ basket.userCoop.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="basket.id" :to="{ name: 'BasketEdit', params: { basketId: basket.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./basket-details.component.ts"></script>
