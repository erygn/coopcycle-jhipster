<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="payment">
        <h2 class="jh-entity-heading" data-cy="paymentDetailsHeading">
          <span v-text="$t('coopcycleApp.payment.detail.title')">Payment</span> {{ payment.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('coopcycleApp.payment.amount')">Amount</span>
          </dt>
          <dd>
            <span>{{ payment.amount }}</span>
          </dd>
          <dt>
            <span v-text="$t('coopcycleApp.payment.grocery')">Grocery</span>
          </dt>
          <dd>
            <div v-if="payment.grocery">
              <router-link :to="{ name: 'GroceryView', params: { groceryId: payment.grocery.id } }">{{ payment.grocery.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="payment.id" :to="{ name: 'PaymentEdit', params: { paymentId: payment.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./payment-details.component.ts"></script>
