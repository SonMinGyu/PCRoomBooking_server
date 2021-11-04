-- call next value for hibernate_sequence;
insert into user (`id`, `name`, `email`, `password`, `student_number`, `major`, `enabled`, `created_at`, `updated_at`) values (1, 'steve', 'steve@naver.com', '1111', '12341234', 'software', 'true', now(), now());

insert into pcroom (`id`, `name`, `building_number`, `layer`, `all_seat_number`, `broken_seat_number`, `useable_seat_number`, `in_use_seat_number`, `enabled` ,`created_at`, `updated_at`) values (1, '6층 PC실', '208', '6', '5', '1', '2', '2', 'true', now(), now());
