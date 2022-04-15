<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="coopcycleApp.basket.home.createOrEditLabel"
          data-cy="BasketCreateUpdateHeading"
          v-text="$t('coopcycleApp.basket.home.createOrEditLabel')"
        >
          Create or edit a Basket
        </h2>
        <div>
          <div class="form-group" v-if="basket.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="basket.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('coopcycleApp.basket.userCoop')" for="basket-userCoop">User Coop</label>
            <select class="form-control" id="basket-userCoop" data-cy="userCoop" name="userCoop" v-model="basket.userCoop">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="basket.userCoop && userCoopOption.id === basket.userCoop.id ? basket.userCoop : userCoopOption"
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
            :disabled="$v.basket.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./basket-update.component.ts"></script>
