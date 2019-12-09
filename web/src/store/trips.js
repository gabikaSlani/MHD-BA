export default [
  {
    startStop: {
      name: 'Blumentál',
    },
    endStop: {
      name: 'Most SNP',
    },
    duration: 13,
    distance: 2,
    actions: [
      {
        type: 'trip',
        trip: {
          delay: 0,
          lowStoried: true,
          routeInfo: {
            name: '3',
            mode: 'tramway',
          },
          tripStops: [
            {
              name: {
                name: 'Blumentál',
              },
              onRequest: false,
              zone: 100,
              departureTime: '16:02',
            },
            {
              name: {
                name: 'Americké nám.',
              },
              onRequest: false,
              zone: 100,
              departureTime: '16:04',
            },
            {
              name: {
                name: 'Mariánska',
              },
              onRequest: false,
              zone: 100,
              departureTime: '16:05',
            },
          ],
          finalStop: {
            name: 'Nám. SNP',
          },
        },
      },
      {
        type: 'trip',
        trip: {
          delay: 0,
          lowStoried: true,
          routeInfo: {
            name: '4',
            mode: 'tramway',
          },
          tripStops: [
            {
              name: {
                name: 'Mariánska',
              },
              onRequest: false,
              zone: 100,
              departureTime: '16:09',
            },
            {
              name: {
                name: 'Kamenné nám.',
              },
              onRequest: false,
              zone: 100,
              departureTime: '16:10',
            },
            {
              name: {
                name: 'SND',
              },
              onRequest: false,
              zone: 100,
              departureTime: '16:12',
            },
            {
              name: {
                name: 'Nám.Ľ.Štúra',
              },
              onRequest: false,
              zone: 100,
              departureTime: '16:14',
            },
            {
              name: {
                name: 'Most SNP',
              },
              onRequest: false,
              zone: 100,
              departureTime: '16:15',
            },
          ],
          finalStop: {
            name: 'Chatam Sofér',
          },
        },
      },
    ],
  },
];
