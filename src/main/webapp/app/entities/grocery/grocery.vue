<template>
  <div>
    <h2 id="page-heading" data-cy="GroceryHeading">
      <span v-text="$t('coopcycleApp.grocery.home.title')" id="grocery-heading">Groceries</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('coopcycleApp.grocery.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'GroceryCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-grocery"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('coopcycleApp.grocery.home.createLabel')"> Create a new Grocery </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && groceries && groceries.length === 0">
      <span v-text="$t('coopcycleApp.grocery.home.notFound')">No groceries found</span>
    </div>
    <div class="table-responsive" v-if="groceries && groceries.length > 0">
      <table class="table table-striped" aria-describedby="groceries">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('coopcycleApp.grocery.adress')">Adress</span></th>
            <th scope="row"><span v-text="$t('coopcycleApp.grocery.basket')">Basket</span></th>
            <th scope="row"><span v-text="$t('coopcycleApp.grocery.userCoop')">User Coop</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="grocery in groceries" :key="grocery.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'GroceryView', params: { groceryId: grocery.id } }">{{ grocery.id }}</router-link>
            </td>
            <td>{{ grocery.adress }}</td>
            <td>
              <div v-if="grocery.basket">
                <router-link :to="{ name: 'BasketView', params: { basketId: grocery.basket.id } }">{{ grocery.basket.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="grocery.userCoop">
                <router-link :to="{ name: 'UserCoopView', params: { userCoopId: grocery.userCoop.id } }">{{
                  grocery.userCoop.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'GroceryView', params: { groceryId: grocery.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'GroceryEdit', params: { groceryId: grocery.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(grocery)"
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
        ><span id="coopcycleApp.grocery.delete.question" data-cy="groceryDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-grocery-heading" v-text="$t('coopcycleApp.grocery.delete.question', { id: removeId })">
          Are you sure you want to delete this Grocery?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-grocery"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeGrocery()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./grocery.component.ts"></script>
