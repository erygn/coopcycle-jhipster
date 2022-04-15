<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="userCoop">
        <h2 class="jh-entity-heading" data-cy="userCoopDetailsHeading">
          <span v-text="$t('coopcycleApp.userCoop.detail.title')">UserCoop</span> {{ userCoop.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('coopcycleApp.userCoop.name')">Name</span>
          </dt>
          <dd>
            <span>{{ userCoop.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('coopcycleApp.userCoop.cooperative')">Cooperative</span>
          </dt>
          <dd>
            <span v-for="(cooperative, i) in userCoop.cooperatives" :key="cooperative.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'CooperativeView', params: { cooperativeId: cooperative.id } }">{{ cooperative.id }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="userCoop.id" :to="{ name: 'UserCoopEdit', params: { userCoopId: userCoop.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./user-coop-details.component.ts"></script>
