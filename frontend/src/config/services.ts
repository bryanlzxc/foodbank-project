import * as env from './../environments/environment.prod';

const prod_services = {
    'test-service': 'https://httpbin.org',
    'admin': {
        'dashboard':'',
        'allocation':'',
        'inventory':'',
        'packing-list':''
    },
    'volunteer': {
        'dashboard':'',
        'inventory':'',
        'packing-list':''
    },
    'beneficiary': {
        'dashboard':'',
        'inventory':'',
        'request':''
    }
};

const dev_services = {
    'test-service': '/mock/test-service.json',
    'admin': {
        'dashboard':'',
        'allocation':'',
        'inventory':'',
        'packing-list':''
    },
    'volunteer': {
        'dashboard':'',
        'inventory':'',
        'packing-list':''
    },
    'beneficiary': {
        'dashboard':'',
        'inventory':'',
        'request':''
    }
};

let services;

env.environment.production ? services = prod_services : services = dev_services;

export default services;
