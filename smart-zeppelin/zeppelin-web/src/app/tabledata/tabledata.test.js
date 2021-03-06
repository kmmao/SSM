/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import TableData from './tabledata.js';

describe('TableData build', function() {
  var td;

  beforeEach(function() {
    console.log(TableData);
    td = new TableData();
  });

  it('should initialize the default value', function() {
    expect(td.columns.length).toBe(0);
    expect(td.rows.length).toBe(0);
    expect(td.comment).toBe('');
  });

  it('should able to create Tabledata from paragraph result', function() {
    td.loadParagraphResult({
      type: 'TABLE',
      msg: 'key\tvalue\na\t10\nb\t20\n\nhello'
    });

    expect(td.columns.length).toBe(2);
    expect(td.rows.length).toBe(2);
    expect(td.comment).toBe('hello');
  });
});
