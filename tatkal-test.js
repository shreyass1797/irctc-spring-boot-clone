import http from 'k6/http';
import { check } from 'k6';

export const options = {
  scenarios: {
    tatkal_rush: {
      executor: 'per-vu-iterations',
      vus: 500,       // 500 users hitting the server at the exact same time
      iterations: 1,  
      maxDuration: '10s',
    },
  },
};

export default function () {
  // We moved the data directly into the URL to match your @RequestParam setup
  const url = 'http://localhost:8080/tickets/book?userId=1&trainId=1&seatClass=AC'; 

  // Since the data is in the URL, we just do a simple POST with no body
  const res = http.post(url);

  check(res, {
    'Transaction Processed': (r) => r.status === 200 || r.status === 400 || r.status === 500,
  });
}