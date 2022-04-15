<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="coopcycleApp.payment.home.createOrEditLabel"
          data-cy="PaymentCreateUpdateHeading"
          v-text="$t('coopcycleApp.payment.home.createOrEditLabel')"
        >
          Create or edit a Payment
        </h2>
        <div>
          <div class="form-group" v-if="payment.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="payment.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('coopcycleApp.payment.amount')" for="payment-amount">Amount</label>
            <input
              type="number"
              class="form-control"
              name="amount"
              id="payment-amount"
              data-cy="amount"
              :class="{ valid: !$v.payment.amount.$invalid, invalid: $v.payment.amount.$invalid }"
              v-model.number="$v.payment.amount.$model"
              required
            />
            <div v-if="$v.payment.amount.$anyDirty && $v.payment.amount.$invalid">
              <small class="form-text text-danger" v-if="!$v.payment.amount.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.payment.amount.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.payment.amount.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('coopcycleApp.payment.grocery')" for="payment-grocery">Grocery</label>
            <select class="form-control" id="payment-grocery" data-cy="grocery" name="grocery" v-model="payment.grocery">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="payment.grocery && groceryOption.id === payment.grocery.id ? payment.grocery : groceryOption"
                v-for="groceryOption in groceries"
                :key="groceryOption.id"
              >
                {{ groceryOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.payment.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./payment-update.component.ts"></script>
