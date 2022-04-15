<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="coopcycleApp.restaurant.home.createOrEditLabel"
          data-cy="RestaurantCreateUpdateHeading"
          v-text="$t('coopcycleApp.restaurant.home.createOrEditLabel')"
        >
          Create or edit a Restaurant
        </h2>
        <div>
          <div class="form-group" v-if="restaurant.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="restaurant.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('coopcycleApp.restaurant.name')" for="restaurant-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="restaurant-name"
              data-cy="name"
              :class="{ valid: !$v.restaurant.name.$invalid, invalid: $v.restaurant.name.$invalid }"
              v-model="$v.restaurant.name.$model"
              required
            />
            <div v-if="$v.restaurant.name.$anyDirty && $v.restaurant.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.restaurant.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.restaurant.name.pattern"
                v-text="$t('entity.validation.pattern', { pattern: 'Name' })"
              >
                This field should follow pattern for "Name".
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('coopcycleApp.restaurant.location')" for="restaurant-location">Location</label>
            <input
              type="text"
              class="form-control"
              name="location"
              id="restaurant-location"
              data-cy="location"
              :class="{ valid: !$v.restaurant.location.$invalid, invalid: $v.restaurant.location.$invalid }"
              v-model="$v.restaurant.location.$model"
              required
            />
            <div v-if="$v.restaurant.location.$anyDirty && $v.restaurant.location.$invalid">
              <small class="form-text text-danger" v-if="!$v.restaurant.location.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('coopcycleApp.restaurant.userCoop')" for="restaurant-userCoop">User Coop</label>
            <select class="form-control" id="restaurant-userCoop" data-cy="userCoop" name="userCoop" v-model="restaurant.userCoop">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="restaurant.userCoop && userCoopOption.id === restaurant.userCoop.id ? restaurant.userCoop : userCoopOption"
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
            :disabled="$v.restaurant.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./restaurant-update.component.ts"></script>
