<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="coopcycleApp.grocery.home.createOrEditLabel"
          data-cy="GroceryCreateUpdateHeading"
          v-text="$t('coopcycleApp.grocery.home.createOrEditLabel')"
        >
          Create or edit a Grocery
        </h2>
        <div>
          <div class="form-group" v-if="grocery.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="grocery.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('coopcycleApp.grocery.adress')" for="grocery-adress">Adress</label>
            <input
              type="text"
              class="form-control"
              name="adress"
              id="grocery-adress"
              data-cy="adress"
              :class="{ valid: !$v.grocery.adress.$invalid, invalid: $v.grocery.adress.$invalid }"
              v-model="$v.grocery.adress.$model"
              required
            />
            <div v-if="$v.grocery.adress.$anyDirty && $v.grocery.adress.$invalid">
              <small class="form-text text-danger" v-if="!$v.grocery.adress.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('coopcycleApp.grocery.basket')" for="grocery-basket">Basket</label>
            <select class="form-control" id="grocery-basket" data-cy="basket" name="basket" v-model="grocery.basket">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="grocery.basket && basketOption.id === grocery.basket.id ? grocery.basket : basketOption"
                v-for="basketOption in baskets"
                :key="basketOption.id"
              >
                {{ basketOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('coopcycleApp.grocery.userCoop')" for="grocery-userCoop">User Coop</label>
            <select class="form-control" id="grocery-userCoop" data-cy="userCoop" name="userCoop" v-model="grocery.userCoop">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="grocery.userCoop && userCoopOption.id === grocery.userCoop.id ? grocery.userCoop : userCoopOption"
                v-for="userCoopOption in userCoops"
                :key="userCoopOption.id"
              >
                {{ userCoopOption.id }}
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
            :disabled="$v.grocery.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./grocery-update.component.ts"></script>
