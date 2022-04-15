<template>
  <div>
    <h2 id="page-heading" data-cy="PaymentHeading">
      <span v-text="$t('coopcycleApp.payment.home.title')" id="payment-heading">Payments</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('coopcycleApp.payment.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PaymentCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-payment"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('coopcycleApp.payment.home.createLabel')"> Create a new Payment </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && payments && payments.length === 0">
      <span v-text="$t('coopcycleApp.payment.home.notFound')">No payments found</span>
    </div>
    <div class="table-responsive" v-if="payments && payments.length > 0">
      <table class="table table-striped" aria-describedby="payments">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('coopcycleApp.payment.amount')">Amount</span></th>
            <th scope="row"><span v-text="$t('coopcycleApp.payment.grocery')">Grocery</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="payment in payments" :key="payment.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PaymentView', params: { paymentId: payment.id } }">{{ payment.id }}</router-link>
            </td>
            <td>{{ payment.amount }}</td>
            <td>
              <div v-if="payment.grocery">
                <router-link :to="{ name: 'GroceryView', params: { groceryId: payment.grocery.id } }">{{ payment.grocery.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PaymentView', params: { paymentId: payment.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PaymentEdit', params: { paymentId: payment.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(payment)"
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
        ><span id="coopcycleApp.payment.delete.question" data-cy="paymentDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-payment-heading" v-text="$t('coopcycleApp.payment.delete.question', { id: removeId })">
          Are you sure you want to delete this Payment?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-payment"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePayment()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./payment.component.ts"></script>
