<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="coopcycleApp.userCoop.home.createOrEditLabel"
          data-cy="UserCoopCreateUpdateHeading"
          v-text="$t('coopcycleApp.userCoop.home.createOrEditLabel')"
        >
          Create or edit a UserCoop
        </h2>
        <div>
          <div class="form-group" v-if="userCoop.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="userCoop.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('coopcycleApp.userCoop.name')" for="user-coop-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="user-coop-name"
              data-cy="name"
              :class="{ valid: !$v.userCoop.name.$invalid, invalid: $v.userCoop.name.$invalid }"
              v-model="$v.userCoop.name.$model"
              required
            />
            <div v-if="$v.userCoop.name.$anyDirty && $v.userCoop.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.userCoop.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label v-text="$t('coopcycleApp.userCoop.cooperative')" for="user-coop-cooperative">Cooperative</label>
            <select
              class="form-control"
              id="user-coop-cooperatives"
              data-cy="cooperative"
              multiple
              name="cooperative"
              v-if="userCoop.cooperatives !== undefined"
              v-model="userCoop.cooperatives"
            >
              <option
                v-bind:value="getSelected(userCoop.cooperatives, cooperativeOption)"
                v-for="cooperativeOption in cooperatives"
                :key="cooperativeOption.id"
              >
                {{ cooperativeOption.id }}
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
            :disabled="$v.userCoop.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./user-coop-update.component.ts"></script>
