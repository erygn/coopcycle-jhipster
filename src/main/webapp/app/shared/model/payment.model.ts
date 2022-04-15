import { IGrocery } from '@/shared/model/grocery.model';

export interface IPayment {
  id?: string;
  amount?: number;
  grocery?: IGrocery | null;
}

export class Payment implements IPayment {
  constructor(public id?: string, public amount?: number, public grocery?: IGrocery | null) {}
}
