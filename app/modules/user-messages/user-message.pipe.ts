import {
      Pipe, PipeTransform, Injectable
} from '@angular/core';
import { LoggingService } from '../../core/index';
import {UserMessage} from "./user-message";

@Pipe({
  name: 'notDisabled',
  pure: false
})
@Injectable()
export class MessageNotDisabledPipe implements PipeTransform {
  transform(items: UserMessage[]): UserMessage[] {
    return items.filter((m) => !m.disabled);
  }
}
