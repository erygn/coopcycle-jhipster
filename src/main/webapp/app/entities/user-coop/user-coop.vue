<template>
  <div>
    <h2 id="page-heading" data-cy="UserCoopHeading">
      <span v-text="$t('coopcycleApp.userCoop.home.title')" id="user-coop-heading">User Coops</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('coopcycleApp.userCoop.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'UserCoopCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-user-coop"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('coopcycleApp.userCoop.home.createLabel')"> Create a new User Coop </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && userCoops && userCoops.length === 0">
      <span v-text="$t('coopcycleApp.userCoop.home.notFound')">No userCoops found</span>
    </div>
    <div class="table-responsive" v-if="userCoops && userCoops.length > 0">
      <table class="table table-striped" aria-describedby="userCoops">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('coopcycleApp.userCoop.name')">Name</span></th>
            <th scope="row"><span v-text="$t('coopcycleApp.userCoop.cooperative')">Cooperative</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="userCoop in userCoops" :key="userCoop.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'UserCoopView', params: { userCoopId: userCoop.id } }">{{ userCoop.id }}</router-link>
            </td>
            <td>{{ userCoop.name }}</td>
            <td>
              <span v-for="(cooperative, i) in userCoop.cooperatives" :key="cooperative.id"
                >{{ i > 0 ? ', ' : '' }}
                <router-link class="form-control-static" :to="{ name: 'CooperativeView', params: { cooperativeId: cooperative.id } }">{{
                  cooperative.id
                }}</router-link>
              </span>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'UserCoopView', params: { userCoopId: userCoop.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'UserCoopEdit', params: { userCoopId: userCoop.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(userCoop)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="coopcycleApp.userCoop.delete.question" data-cy="userCoopDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-userCoop-heading" v-text="$t('coopcycleApp.userCoop.delete.question', { id: removeId })">
          Are you sure you want to delete this User Coop?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-userCoop"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeUserCoop()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./user-coop.component.ts"></script>
