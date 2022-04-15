<template>
  <div>
    <b-dropdown-item to="/user-coop">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.userCoop')">User Coop</span>
    </b-dropdown-item>
    <b-dropdown-item to="/grocery">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.grocery')">Grocery</span>
    </b-dropdown-item>
    <b-dropdown-item to="/payment">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.payment')">Payment</span>
    </b-dropdown-item>
    <b-dropdown-item to="/basket">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.basket')">Basket</span>
    </b-dropdown-item>
    <b-dropdown-item to="/product">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.product')">Product</span>
    </b-dropdown-item>
    <b-dropdown-item to="/restaurant">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.restaurant')">Restaurant</span>
    </b-dropdown-item>
    <b-dropdown-item to="/cooperative">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.cooperative')">Cooperative</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
