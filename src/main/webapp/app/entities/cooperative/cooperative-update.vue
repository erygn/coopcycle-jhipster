<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="coopcycleApp.cooperative.home.createOrEditLabel"
          data-cy="CooperativeCreateUpdateHeading"
          v-text="$t('coopcycleApp.cooperative.home.createOrEditLabel')"
        >
          Create or edit a Cooperative
        </h2>
        <div>
          <div class="form-group" v-if="cooperative.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="cooperative.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('coopcycleApp.cooperative.name')" for="cooperative-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="cooperative-name"
              data-cy="name"
              :class="{ valid: !$v.cooperative.name.$invalid, invalid: $v.cooperative.name.$invalid }"
              v-model="$v.cooperative.name.$model"
              required
            />
            <div v-if="$v.cooperative.name.$anyDirty && $v.cooperative.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.cooperative.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
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
            :disabled="$v.cooperative.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./cooperative-update.component.ts"></script>
